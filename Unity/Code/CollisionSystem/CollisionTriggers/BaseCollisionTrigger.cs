using Example.GameElements.CollisionSystem.CollisionRules;
using Example.GameElements.CollisionSystem.Configs;
using UnityEngine;
using Zenject;

namespace Example.GameElements.CollisionSystem.CollisionTriggers
{
    public class BaseCollisionTrigger<T> : BaseTrigger where T : BaseRule
    {
        [Inject] private ICollisionSystem<T> _collisionSystem;

        [SerializeField] private CollisionConfig [] _mainConfig;
        
        public override void OnCollisionGameObject(Collider collider, GameObject otherGameObject)
        {
            Collision(_mainConfig, collider, otherGameObject);
        }
        
        private void Collision(CollisionConfig [] collisionTypes, Collider other,
            GameObject otherGameObject)
        {
            foreach (var collision in collisionTypes)
            {
                var layer = other.gameObject.layer;
                if (layer == (int)collision.CollisionType)
                {
                    _collisionSystem.ApproveCollision(collision.CollisionType, otherGameObject);
                }
            }
        }
    }
}