using Example.Framework.Core.Signals.Domain;

namespace Example.Framework.Core.Signals
{
    public interface ISignalSubscriber
    {
        void Subscribe<TSignal>(SignalHandler<TSignal> handler)
            where TSignal : class, ISignal;

        void Unsubscribe<TSignal>(SignalHandler<TSignal> handler)
            where TSignal : class, ISignal;
    }
}
