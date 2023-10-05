import { getTimeAgo } from "../../util/formatDateSign";
interface Props {
  isMine: boolean;
  message: string;
  profileImage: string;
  dateTime: string;
  nickname: string;
}

const ChatContentItem = ({ isMine, message, profileImage, dateTime, nickname }: Props) => {
  return isMine ? (
    <div className="clearfix flex justify-end items-end mr-2 mt-2 gap-1">
      <div className=" text-xs text-gray-400 mt-4">{getTimeAgo(dateTime)}</div>
      <div className="bg-green-300 w-3/5 me-1 p-2 rounded-lg clearfix text-sm">{message}</div>
    </div>
  ) : (
    <div className="clearfix flex justify-start items-center ml-2 mt-2 gap-1">
      <div className=" flex flex-col items-center">
        <img src={profileImage} className="w-[40px] h-[40px] rounded-full" />
        <p className=" text-xs text-gray-700">{nickname}</p>
      </div>
      <div
        className="bg-gray-300  max-w-[60%] my-2 p-2 rounded-lg clearfix text-sm "
        style={{
          wordWrap: "break-word",
        }}
      >
        {message}
      </div>
      <p className=" text-xs text-gray-400 mt-4">{getTimeAgo(dateTime)}</p>
    </div>
  );
};
export default ChatContentItem;
