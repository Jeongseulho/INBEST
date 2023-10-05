import { useEffect } from "react";
import ChatContentItem from "./ChatContentItem";
import { Chat } from "../../type/Chat";
import stompStore from "../../store/stompStore";
import { useState } from "react";
import userStore from "../../store/userStore";
import { MdOutlineGroupOff } from "react-icons/md";

interface Props {
  simulationSeq: number;
  scrollRef: React.ForwardedRef<HTMLDivElement>;
}
const ChattingArea = ({ simulationSeq, scrollRef }: Props) => {
  const { client } = stompStore();
  const [chatList, setChatList] = useState<Chat[]>([]);
  const { userInfo } = userStore();

  useEffect(() => {
    if (simulationSeq === 0) return;

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
      nickname: userInfo?.nickname,
    };
    client?.publish({ destination: `/app/chat.message.${simulationSeq}`, body: JSON.stringify(enterData) });

    return () => {
      const exitData = {
        type: "exit",
        simulationSeq,
        userSeq: userInfo?.seq,
        message: `${userInfo?.nickname}님이 퇴장하셨습니다.`,
        dateTime: new Date(),
        profileImgSearchName: userInfo?.profileImgSearchName,
        nickname: userInfo?.nickname,
      };
      client?.publish({ destination: "/app/chat.message." + simulationSeq, body: JSON.stringify(exitData) });
      subscription?.unsubscribe();
    };
  }, [client, simulationSeq, userInfo?.seq, userInfo?.nickname, userInfo?.profileImgSearchName]);

  return simulationSeq === 0 ? (
    <div className="overflow-y-scroll flex-col items-center justify-center min-h-[80%]">
      <MdOutlineGroupOff
        style={{
          color: "#35493a",
          width: "100px",
          height: "100px",
          margin: "auto",
          marginTop: "100px",
        }}
      />
      <p className=" text-center text-myGray font-bold">참여한 그룹이 없어요.</p>
    </div>
  ) : (
    <div className="overflow-y-scroll flex-col min-h-[80%]" ref={scrollRef}>
      {chatList.map((chat, index) => (
        <ChatContentItem
          key={index}
          isMine={chat.userSeq === userInfo?.seq}
          message={chat.message}
          profileImage={chat.profileImgSearchName}
          dateTime={chat.dateTime}
          nickname={chat.nickname}
        />
      ))}
    </div>
  );
};
export default ChattingArea;
