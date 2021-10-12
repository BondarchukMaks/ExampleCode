using Core.Signals;
using Example.Framework.Core.Signals;
using Example.GameElements.CollisionSystem.CollisionRules.BoostRules.Signals;

namespace Example.GameElements.CollisionSystem.CollisionRules.BoostRules
{
    public class SpeedRule : BaseBoostRule
    {
        public SpeedRule(ISignalSender signalSender) : base(signalSender, BoostType.SPEED)
        {
        }

        public override void SetRule()
        {
            base.SetRule();

            _signalSender.SendEmpty<SpeedBoostSignal>();
        }
    }
}