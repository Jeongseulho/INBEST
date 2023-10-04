interface Props {
  isMine: boolean;
  message: string;
  profileImage: string;
  dateTime: string;
  nickname: string;
}

const ChatContentItem = ({ isMine, message, profileImage, dateTime, nickname }: Props) => {
  return isMine ? (
    <div className="clearfix flex justify-end items-end ml-1 gap-1">
      <div className=" text-xs text-gray-400 mt-4">{dateTime}</div>
      <div className="bg-green-300 w-3/5 me-1 p-2 rounded-lg clearfix text-sm">{message}</div>
    </div>
  ) : (
    <div className="clearfix flex justify-start items-center ml-1 mt-1 gap-1">
      <div className=" flex flex-col items-center">
        <img src={profileImage} className="w-[40px] h-[40px]" />
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
      <p className=" text-xs text-gray-400 mt-4">{dateTime}</p>
    </div>
  );
};
export default ChatContentItem;
