import { useCallback, useEffect, useRef, useState } from "react";
import { Client } from "@stomp/stompjs";
import SockJs from "sockjs-client";
import { useAuth } from "../contexts/AuthContext";

export function useChatSocket() {
  const BASE_URL = import.meta.env.VITE_BASE_API_URL;
  const clientRef = useRef(null);
  const [connected, setConnected] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const { user } = useAuth();

  const connect = useCallback(() => {
    if (!user) return;
    console.log(
      "user socker url:",
      `/user/${user.username}/topic/notification`
    );
    if (clientRef.current?.connected) return;
    const c = new Client(
      {
        webSocketFactory: () => new SockJs(`${BASE_URL}/ws`),
        reconnectDelay: 4000,
        onConnect: () => {
          setConnected(true);
          c.subscribe("/topic/notification", (msg) => {
            try {
              const obj = JSON.parse(msg.body);
              console.log(obj);
              setNotifications((prev) => [...prev, obj]);
            } catch (err) {
              console.error(err);
            }
          });
          c.subscribe(`/user/${user.username}/topic/notification`, (msg) => {
            try {
              const obj = JSON.parse(msg.body);
              console.log(obj);
              setNotifications((prev) => [...prev, obj]);
            } catch (err) {
              console.error(err);
            }
          });
        },
        onDisconnect: () => setConnected(false),
        onStompError: (frame) => {
          console.error("Broker error", frame.headers["message"], frame.body);
        },
        onWebSocketClose: () => setConnected(false),
      },
      [BASE_URL]
    );

    c.activate();

    clientRef.current = c;
  }, [BASE_URL, user]);

  const disconnect = useCallback(() => {
    if (clientRef.current) {
      clientRef.current.deactivate();
    }
  }, []);

  const clearNotifications = useCallback(() => {
    setNotifications([]);
  }, []);

  useEffect(() => {
    connect();
    return () => disconnect();
  }, [connect, disconnect]);

  return { connected, notifications, clearNotifications };
}
