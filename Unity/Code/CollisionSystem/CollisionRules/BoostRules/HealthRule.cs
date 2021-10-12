using Core.Signals;
using Example.Framework.Core.Signals;
using Example.GameElements.CollisionSystem.CollisionRules.BoostRules.Signals;

namespace Example.GameElements.CollisionSystem.CollisionRules.BoostRules
{
    public class HealthRule : BaseBoostRule
    {
        public HealthRule(ISignalSender signalSender) : base(signalSender, BoostType.HEALTH)
        {
        }
        
        public override void SetRule()
        {
            base.SetRule();

            _signalSender.SendEmpty<HealthBoostSignal>();
        }
    }
}