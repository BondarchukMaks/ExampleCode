using Zenject;

namespace Example.Framework.Core.Signals.Installers
{
    public class SignalInstaller : Installer<SignalInstaller>
    {
        public override void InstallBindings()
        {
            Container.BindInterfacesAndSelfTo<SignalManager>().AsSingle().NonLazy();
        }
    }
}