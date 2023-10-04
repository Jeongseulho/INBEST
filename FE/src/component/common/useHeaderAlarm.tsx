import { useEffect } from "react";
import userStore from "../../store/userStore";
import { Client } from "@stomp/stompjs";
import { useState } from "react";
import stompStore from "../../store/stompStore";
import { Alarm } from "../../type/Alarm";

export const useHeaderAlarm = () => {
  const { accessToken, userInfo } = userStore();
  const [alarmList, setAlarmList] = useState<Alarm[] | []>([]);
  const { setClient } = stompStore();
  const [shakeBell, setShakeBell] = useState(false);

  useEffect(() => {
    if (shakeBell) {
      const bell = document.getElementById("bell");
      bell?.classList.add("shake-bell");
      setTimeout(() => {
        bell?.classList.remove("shake-bell");
        setShakeBell(false);
      }, 3000);
    }
  }, [shakeBell]);

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

          newClient.subscribe(`/topic/notification.${userInfo?.seq}`, (msg) => {
            setAlarmList((prev) => {
              const body = JSON.parse(msg.body);
              const isDuplicate = prev.some((alarm) => alarm.id === body.id);
              if (isDuplicate) return prev;
              else return [...prev, JSON.parse(msg.body)];
            });
            setShakeBell(true);
          });

          newClient.publish({ destination: "/app/notification.resend." + userInfo?.seq });
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
  }, [accessToken, userInfo?.seq, setClient]);

  return { alarmList, setAlarmList };
};
