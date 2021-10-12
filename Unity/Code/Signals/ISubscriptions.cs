namespace Example.Framework.Core.Signals
{
    public interface ISubscriptions<in TBaseSignal>
    {
        void Invoke(TBaseSignal signal);
    }
}
