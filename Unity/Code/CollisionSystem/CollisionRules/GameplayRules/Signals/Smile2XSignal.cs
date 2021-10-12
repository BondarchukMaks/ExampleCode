using Core.Signals.Domain;

namespace Example.GameElements.CollisionSystem.CollisionRules.GameplayRules.Signals
{
    public class Smile2XSignal : Signal
    {
        public bool IsActive { get; set; }

        public override void Clear()
        {
            IsActive = false;
        }
    }
}