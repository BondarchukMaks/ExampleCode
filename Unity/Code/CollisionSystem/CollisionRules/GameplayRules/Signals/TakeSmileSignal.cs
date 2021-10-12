using Core.Signals.Domain;

namespace Example.GameElements.CollisionSystem.CollisionRules.GameplayRules.Signals
{
    public class TakeSmileSignal : Signal
    {
        public int CountSmiles { get; set; }

        public override void Clear()
        {
            CountSmiles = 0;
        }
    }
}