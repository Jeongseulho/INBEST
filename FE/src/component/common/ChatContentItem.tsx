interface Props {
  isMine: boolean;
  message: string;
  profileImage?: string;
}

const ChatContentItem = ({ isMine, message, profileImage }: Props) => {
  return isMine ? (
    <div className="clearfix flex justify-start items-center ml-2">
      <img src={profileImage} className="w-[40px] h-[40px]" />
      <div className="bg-gray-300 w-3/5 mx-4 my-2 p-2 rounded-lg clearfix text-sm">{message}</div>
    </div>
  ) : (
    <div className="clearfix flex justify-end">
      <div className="bg-green-300 w-3/5 mx-4 my-2 p-2 rounded-lg clearfix text-sm">{message}</div>
    </div>
  );
};
export default ChatContentItem;
