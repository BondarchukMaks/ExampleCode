using Example.Framework.Core.Signals.Domain;

namespace Example.Framework.Core.Signals
{    
    public interface ISignalSender
    {
        void Send<TSignal>(TSignal signal)
            where TSignal : class, ISignal;

        void SendEmpty<TSignal>()
            where TSignal : class, ISignal;

        TSignal GetSignal<TSignal>()
            where TSignal : class, ISignal;
    }
}
