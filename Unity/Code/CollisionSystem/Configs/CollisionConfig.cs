using UnityEngine;

namespace Example.GameElements.CollisionSystem.Configs
{
    [CreateAssetMenu(fileName = "CollisionConfig", menuName = "Configs/Collisions/CollisionConfig")]
    public class CollisionConfig : ScriptableObject,ICollisionConfig
    {
        [SerializeField] private ECollisionType _collisionType;

        public ECollisionType CollisionType => _collisionType;
    }
}