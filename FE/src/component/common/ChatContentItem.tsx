interface Props {
  isMine: boolean;
  message: string;
  profileImage: string;
  dateTime: string;
}

const ChatContentItem = ({ isMine, message, profileImage, dateTime }: Props) => {
  return isMine ? (
    <div className="clearfix flex justify-start items-center ml-1 gap-1">
      <img src={profileImage} className="w-[40px] h-[40px]" />
      <div className="bg-gray-300 w-3/5 my-2 p-2 rounded-lg clearfix text-sm">{message}</div>
      <p className=" text-xs text-gray-400 mt-4">{dateTime}</p>
    </div>
  ) : (
    <div className="clearfix flex justify-end items-end ml-1 gap-1">
      <div className=" text-xs text-gray-400 mt-4">{dateTime}</div>
      <div className="bg-green-300 w-3/5 me-1 p-2 rounded-lg clearfix text-sm">{message}</div>
    </div>
  );
};
export default ChatContentItem;
