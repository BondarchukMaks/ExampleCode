using Core.PopUp;
using ProjectScene.UISystem.Popups.Damage;
using UnityEngine;

namespace Example.GameElements.CollisionSystem.CollisionRules.GameplayRules
{
    public class PlayerDamageRule : BaseGameplayRule
    {
        private IPopupSystem _popupSystem;

        public PlayerDamageRule(IPopupSystem popupSystem)
        {
            _popupSystem = popupSystem;
        }

        public override void SetRule()
        {
            _popupSystem.OpenPopUp(new DamagePopupModel());
        }
    }
}