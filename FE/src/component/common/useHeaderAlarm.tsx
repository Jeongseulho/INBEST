import { useEffect } from "react";
import userStore from "../../store/userStore";
import { Client } from "@stomp/stompjs";
import { useState } from "react";
import stompStore from "../../store/stompStore";
import { Alarm } from "../../type/Alarm";

export const useHeaderAlarm = () => {
  const { accessToken, userInfo } = userStore();
  const [shakeBell, setShakeBell] = useState(false);
  const [alarmList, setAlarmList] = useState<Alarm[]>([]);
  const { setClient } = stompStore();

  useEffect(() => {
    if (accessToken) {
      const newClient = new Client({
        brokerURL: import.meta.env.VITE_APP_STOMP_BASE_URL,

        connectHeaders: {
          Authorization: `Bearer ${accessToken}`,
        },
        beforeConnect: () => {
          console.log("Connecting to WebSocket");
        },
        onConnect: () => {
          console.log("Connected to WebSocket");
          newClient.subscribe(`/topic/notification.${userInfo?.seq}`, (message) => {
            setShakeBell(true);
            setTimeout(() => {
              setShakeBell(false);
            }, 3000);

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
      setClient(newClient);
      newClient.activate();
    }
  }, [accessToken]);

  return { shakeBell, alarmList, setAlarmList };
};
