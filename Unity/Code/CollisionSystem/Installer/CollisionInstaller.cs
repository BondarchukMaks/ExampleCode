using Example.GameElements.CollisionSystem.CollisionSystems;
using Zenject;

namespace Example.GameElements.CollisionSystem.Installer
{
    public class CollisionInstaller : Installer<CollisionInstaller>
    {
        public override void InstallBindings()
        {
            Container.BindInterfacesTo<InventoryCollisionSystem>().AsSingle().NonLazy();
            Container.BindInterfacesTo<BoostCollisionSystem>().AsSingle().NonLazy();
            Container.BindInterfacesTo<GameplayCollisionSystem>().AsSingle().NonLazy();
        }
    }
}