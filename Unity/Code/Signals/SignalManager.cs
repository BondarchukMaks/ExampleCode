using System;
using Example.Framework.Core.Signals.Domain;

namespace Example.Framework.Core.Signals
{
    public sealed class SignalManager : IDisposable, ISignalSender, ISignalSubscriber
    {
        private readonly SignalDispatcher<ISignal> _signalDispatcher;
        private readonly SignalPool<ISignal> _signalPool;

        public SignalManager()
        {
            _signalDispatcher = new SignalDispatcher<ISignal>();
            _signalPool = new SignalPool<ISignal>();
        }

        public void Dispose()
        {
            _signalPool.Clear();
        }

        public void Subscribe<TSignal>(SignalHandler<TSignal> handler) 
            where TSignal : class, ISignal
        {
            _signalDispatcher.Add(handler);
        }

        public void Unsubscribe<TSignal>(SignalHandler<TSignal> handler)
            where TSignal : class, ISignal
        {
            _signalDispatcher.Remove(handler);
        }

        public void Send<TSignal>(TSignal signal) 
            where TSignal : class, ISignal
        {
            _signalDispatcher.Dispatch(signal);
            _signalPool.Recycle(signal);        
        }

        public void SendEmpty<TSignal>()
            where TSignal : class, ISignal
        {
            TSignal signal = GetSignal<TSignal>();
            Send(signal);
        }

        public TSignal GetSignal<TSignal>() 
            where TSignal : class, ISignal
        {
            return _signalPool.GetSignal<TSignal>();
        }
    }
}