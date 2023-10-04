import { useEffect } from "react";
import ChatContentItem from "./ChatContentItem";
import { Chat } from "../../type/Chat";
import stompStore from "../../store/stompStore";
import { useState } from "react";
import userStore from "../../store/userStore";

interface Props {
  simulationSeq: number;
}
const ChattingArea = ({ simulationSeq }: Props) => {
  const { client } = stompStore();
  const [chatList, setChatList] = useState<Chat[]>([]);
  const { userInfo } = userStore();

  useEffect(() => {
    const subscription = client?.subscribe(`/topic/chat.${simulationSeq}`, (msg) => {
      const chat = JSON.parse(msg.body);
      setChatList((prev) => [...prev, chat]);
    });

    const enterData = {
      type: "enter",
      simulationSeq,
      userSeq: userInfo?.seq,
      message: `${userInfo?.nickname}님이 입장하셨습니다.`,
      dateTime: new Date(),
      profileImgSearchName: userInfo?.profileImgSearchName,
    };
    client?.publish({ destination: "/app/chat.enter." + simulationSeq, body: JSON.stringify(enterData) });

    return () => {
      const exitData = {
        type: "exit",
        simulationSeq,
        userSeq: userInfo?.seq,
        message: `${userInfo?.nickname}님이 퇴장하셨습니다.`,
        dateTime: new Date(),
        profileImgSearchName: userInfo?.profileImgSearchName,
      };
      client?.publish({ destination: "/app/chat.exit." + simulationSeq, body: JSON.stringify(exitData) });
      subscription?.unsubscribe();
    };
  }, [client, simulationSeq, userInfo?.seq, userInfo?.nickname, userInfo?.profileImgSearchName]);

  return (
    <div className="overflow-y-scroll flex-col min-h-[80%]">
      {chatList.map((chat, index) => (
        <ChatContentItem
          key={index}
          isMine={chat.userSeq === userInfo?.seq}
          message={chat.message}
          profileImage={chat.profileImgSearchName}
          dateTime={chat.dateTime}
        />
      ))}
    </div>
  );
};
export default ChattingArea;
