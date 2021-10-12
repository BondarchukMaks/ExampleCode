using System;
using System.Collections.Generic;
using Example.Framework.Core.Logger;

namespace Example.Framework.Core.Signals
{
    public class Subscriptions<TSignal, TBaseSignal> : ISubscriptions<TBaseSignal>
        where TSignal : TBaseSignal
    {
        private readonly HashSet<SignalHandler<TSignal>> _handlers;
        private readonly List<SignalHandler<TSignal>> _pendingAdd;
        private readonly List<SignalHandler<TSignal>> _pendingRemove;

        private bool _invocationInProgress;

        public Subscriptions()
        {
            _handlers = new HashSet<SignalHandler<TSignal>>();
            _pendingAdd = new List<SignalHandler<TSignal>>();
            _pendingRemove = new List<SignalHandler<TSignal>>();
        }

        public void Invoke(TBaseSignal signal)
        {
            _invocationInProgress = true;
            
            foreach (var handler in _handlers)
            {
                try
                {
                    handler?.Invoke((TSignal) signal);
                }
                catch (Exception e)
                {
                    SystemLog.LogError(SignalsLogType.SignalDispatch, "Exception in signal {0} handler {1}: {2}",
                        typeof(TSignal).FullName, handler?.GetType().FullName, e);
                }
            }

            _invocationInProgress = false;
            ProcessPendingChanges();
        }

        public void AddHandler(SignalHandler<TSignal> handler)
        {
            if (handler == null)
            {
                throw new ArgumentNullException(nameof(handler), "Signal handler can't be null");
            }

            if (!_invocationInProgress)
            {
                if (!_handlers.Add(handler))
                {
                    SystemLog.LogWarning(SignalsLogType.SignalDispatch,
                        "Duplicate subscription attempt to signal {0} by handler {1}",
                        typeof(TSignal), handler);
                }
            }
            else
            {
                _pendingRemove.RemoveAll(h => h == handler);
                _pendingAdd.Add(handler);
            }
        }

        public void RemoveHandler(SignalHandler<TSignal> handler)
        {
            if (handler == null)
            {
                throw new ArgumentNullException(nameof(handler), "Signal handler can't be null");
            }

            if (!_invocationInProgress)
            {
                _handlers.Remove(handler);
            }
            else
            {
                _pendingAdd.RemoveAll(h => h == handler);
                _pendingRemove.Add(handler);
            }
        }

        private void ProcessPendingChanges()
        {
            foreach (var handler in _pendingAdd)
            {
                AddHandler(handler);
            }

            _pendingAdd.Clear();

            foreach (var handler in _pendingRemove)
            {
                RemoveHandler(handler);
            }

            _pendingRemove.Clear();
        }
    }
}
