using Core.Signals;
using Example.Framework.Core.Signals;
using Example.GameElements.CollisionSystem.CollisionRules.GameplayRules.Signals;
using Example.Info;
using UnityEngine;

namespace Example.GameElements.CollisionSystem.CollisionRules.GameplayRules
{
    public class SmileRule : BaseGameplayRule
    { 
        private IGameInfo _info;
        private ISignalSender _signalSender;
        private ISignalSubscriber _signalSubscriber;

        private bool _smile2X;
        
        public SmileRule(IGameInfo info, ISignalSender signalSender, ISignalSubscriber signalSubscriber)
        {
            _info = info;
            _signalSender = signalSender;
            _signalSubscriber = signalSubscriber;
            
            _signalSubscriber.Subscribe<Smile2XSignal>(Set2xBoost);
        }

        public override void SetRule()
        {
            var signal = _signalSender.GetSignal<TakeSmileSignal>();
            var countSmiles = _smile2X? 2 : 1;
            
            _info.Smiles.SetValue(_info.Smiles.CurrentValue + countSmiles);
            signal.CountSmiles = countSmiles;
            
            _signalSender.Send(signal);
        }
        
        private void Set2xBoost(Smile2XSignal signal)
        {
            _smile2X = signal.IsActive;
        }
    }
}