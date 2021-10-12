namespace Example.Framework.Core.Signals
{
    public delegate void SignalHandler<in TSignal>(TSignal signal);
}
