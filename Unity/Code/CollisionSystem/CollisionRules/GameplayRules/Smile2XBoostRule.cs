using Core.Signals;
using Core.Utils.MonoCoroutine;
using Example.Framework.Core.Signals;
using Example.GameElements.CollisionSystem.CollisionRules.GameplayRules.Signals;

namespace Example.GameElements.CollisionSystem.CollisionRules.GameplayRules
{
    public class Smile2XBoostRule : BaseGameplayRule
    {
        private ISignalSender _signalSender;
        private ICoroutineManager _coroutineManager;
        
        public Smile2XBoostRule(ISignalSender signalSender, ICoroutineManager coroutineManager)
        {
            _signalSender = signalSender;
            _coroutineManager = coroutineManager;
        }

        public override void SetRule()
        {
            SendSignal(true);

            _coroutineManager.WaitForSeconds(5, () => SendSignal(false));
        }

        private void SendSignal(bool isActive)
        {
            var signal = _signalSender.GetSignal<Smile2XSignal>();
            signal.IsActive = isActive;
            _signalSender.Send(signal);
        }
    }
}