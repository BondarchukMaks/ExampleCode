using UnityEngine;

namespace Example.GameElements.CollisionSystem.Configs.Rule
{
    [CreateAssetMenu(fileName = "CollisionConfig", menuName = "Configs/Collisions/RulesConfig")]
    public class GroupRuleConfig : ScriptableObject,IRulesConfig
    {
        [SerializeField] private CollisionConfig [] _configs;

        public ICollisionConfig[] Configs => _configs;
    }
}