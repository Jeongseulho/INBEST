import { useEffect } from "react";
import userStore from "../../store/userStore";
import { Client } from "@stomp/stompjs";

export const useHeaderAlarm = () => {
  const { accessToken, userInfo } = userStore();

  useEffect(() => {
    if (accessToken) {
      const client = new Client({
        brokerURL: import.meta.env.VITE_APP_STOMP_BASE_URL,

        connectHeaders: {
          Authorization: `Bearer ${accessToken}`,
        },
        beforeConnect: () => {
          console.log("Connecting to WebSocket");
        },
        onConnect: () => {
          console.log("Connected to WebSocket");
          client.subscribe(`/topic/notification.${userInfo?.seq}`, (message) => {
            console.log(message);
          });
        },
        onDisconnect: () => {
          console.log("Disconnected from WebSocket");
        },
        onWebSocketClose: (closeEvent) => {
          console.log("WebSocket closed", closeEvent);
        },
        onWebSocketError: (error) => {
          console.log("WebSocket error: ", error);
        },
        heartbeatIncoming: 0,
        heartbeatOutgoing: 0,
      });

      client.activate();
    }
  }, [accessToken]);
};
