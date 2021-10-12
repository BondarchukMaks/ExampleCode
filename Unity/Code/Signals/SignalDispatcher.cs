using System;
using System.Collections.Generic;
using Example.Framework.Core.Signals.Domain;
using Example.Framework.Core.Extensions;
using Example.Framework.Core.Logger;

namespace Example.Framework.Core.Signals
{
    public class SignalDispatcher<TBaseSignal> where TBaseSignal : ISignal
    {
        private readonly Type _baseSignalType;
        private readonly Dictionary<Type, ISignalSubscription> _subscriptions;

        public SignalDispatcher()
        {
            _subscriptions = new Dictionary<Type, ISignalSubscription>();
            _baseSignalType = typeof(ISignal);
        }

        public void Dispatch(TBaseSignal signal)
        {
            
            try
            {
                foreach (var signalType in signal.GetType().GetTypes(true))
                {
                    if (!_baseSignalType.IsAssignableFrom(signalType))
                    {
                        continue;
                    }

                    if (_subscriptions.TryGetValue(signalType, out var subscriptions))
                    {
                        subscriptions.Invoke(signal);
                    }
                }
            }
            catch (Exception e)
            {
                SystemLog.LogError(SignalsLogType.SignalDispatch, "Signal dispatch exception: {0}", e);
            }
        }

        public void Add<TSignal>(SignalHandler<TSignal> handler)
            where TSignal : TBaseSignal
        {
            if (!_subscriptions.TryGetValue(typeof(TSignal), out var subscriptions))
            {
                _subscriptions.Add(typeof(TSignal), subscriptions = new SignalSubscription<TSignal>());
            }

            ((SignalSubscription<TSignal>) subscriptions).AddHandler(handler);
        }

        public void Remove<TSignal>(SignalHandler<TSignal> handler)
            where TSignal : TBaseSignal
        {
            if (_subscriptions.TryGetValue(typeof(TSignal), out var subscriptions))
            {
                ((SignalSubscription<TSignal>) subscriptions).RemoveHandler(handler);
            }
        }

        private class SignalSubscription<TSignal> : Subscriptions<TSignal, ISignal>, ISignalSubscription
            where TSignal : ISignal
        {
        }

        private interface ISignalSubscription : ISubscriptions<ISignal>
        {
        }
    }
}
