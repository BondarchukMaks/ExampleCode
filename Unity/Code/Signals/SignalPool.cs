using System;
using System.Collections.Generic;
using System.Linq;
using Example.Framework.Core.Signals.Domain;

namespace Example.Framework.Core.Signals
{
    public class SignalPool<TBaseSignal> where TBaseSignal : ISignal
    {
        private readonly Dictionary<Type, Queue<TBaseSignal>> _signalPool;

        public SignalPool()
        {
            _signalPool = new Dictionary<Type, Queue<TBaseSignal>>(new SignalTypeComparer());
            
            var signalTypes = (from domainAssembly in AppDomain.CurrentDomain.GetAssemblies()
                from assemblyType in domainAssembly.GetTypes()
                where typeof(TBaseSignal).IsAssignableFrom(assemblyType)
                select assemblyType).ToArray();

            foreach (var signalType in signalTypes)
            {
                if (!signalType.IsAbstract)
                {
                    _signalPool[signalType] = new Queue<TBaseSignal>();
                }
            }
        }

        public TSignal GetSignal<TSignal>()
            where TSignal : TBaseSignal
        {
            var signal = AllocSignal(typeof (TSignal));
            if (signal != null)
            {
                return (TSignal) signal;
            }

            throw new NotSupportedException($"{typeof(TBaseSignal).Name} {typeof (TSignal).Name}  is not supported");
        }

        public TBaseSignal GetSignal(Type signalType)
        { 
            if (_signalPool.ContainsKey(signalType))
            {
                var signal = AllocSignal(signalType);
                if (signal != null)
                {
                    return signal;
                }
            }

            throw new NotSupportedException($"{typeof(TBaseSignal).Name} {signalType} is not supported");
        }

        public void Recycle(TBaseSignal signal)
        {
            signal.Clear();

            Queue<TBaseSignal> queue;
            if (_signalPool.TryGetValue(signal.GetType(), out queue))
            {
                queue.Enqueue(signal);
            }
        }

        public void Clear()
        {
            foreach (var signalQueue in _signalPool.Values)
            {
                signalQueue.Clear();
            }
        }

        private TBaseSignal AllocSignal(Type signalType)
        {
            Queue<TBaseSignal> queue;
            if (_signalPool.TryGetValue(signalType, out queue))
            {
                if (queue.Count == 0)
                {
                    return (TBaseSignal) Activator.CreateInstance(signalType);
                }

                return queue.Dequeue();
            }

            return default;
        }

        private class SignalTypeComparer : IEqualityComparer<Type>
        {
            public bool Equals(Type x, Type y)
            {
                return x == y;
            }

            public int GetHashCode(Type obj)
            {
                return obj.GetHashCode();
            }
        }
    }
}