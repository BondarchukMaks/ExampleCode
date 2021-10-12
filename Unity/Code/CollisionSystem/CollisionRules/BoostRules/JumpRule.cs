using Core.Signals;
using Example.Framework.Core.Signals;
using Example.GameElements.CollisionSystem.CollisionRules.BoostRules.Signals;
using MoreMountains.NiceVibrations;

namespace Example.GameElements.CollisionSystem.CollisionRules.BoostRules
{
    public class JumpRule : BaseBoostRule
    {
        public JumpRule(ISignalSender signalSender) : base(signalSender, BoostType.JUMP)
        {
        }
        
        public override void SetRule()
        {
            base.SetRule();

            MMVibrationManager.Haptic(HapticTypes.LightImpact);
            _signalSender.SendEmpty<JumpBoostSignal>();
        }
    }
}