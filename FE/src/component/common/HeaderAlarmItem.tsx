import { AiOutlineCheckCircle, AiOutlineCloseCircle } from "react-icons/ai";
import stompStore from "../../store/stompStore";
import { Alarm } from "../../type/Alarm";
import userStore from "../../store/userStore";
import { joinGroup } from "../../api/group";
import { useMutation } from "react-query";
import { toast } from "react-toastify";

interface Props {
  setAlarmList: React.Dispatch<React.SetStateAction<Alarm[]>>;
  id: string;
  alarm: Alarm;
}

const HeaderAlarmItem = ({ setAlarmList, id, alarm }: Props) => {
  const { client } = stompStore();
  const { userInfo } = userStore();

  const { mutate } = useMutation((simulationSeq: number) => joinGroup(simulationSeq), {
    onSuccess: () => {
      toast.success("그룹에 참여되었습니다, [모의투자]탭의 [내 그룹]에서 확인해보세요.");
    },
    onError: () => {
      toast.error("이미 진행중인 그룹입니다, 참여할 수 없습니다.");
    },
    onSettled: () => {
      onReadAlarm();
    },
  });

  const onReadAlarm = () => {
    client?.publish({ destination: `/app/notification.read.${userInfo?.seq}`, body: JSON.stringify(alarm) });
    setAlarmList((prev) => prev.filter((alarm) => alarm.id !== id));
  };

  return (
    <div className=" flex flex-col px-4 py-2">
      <p className=" text-md font-bold">{alarm.title}</p>
      <p className=" text-sm text-gray-600">{alarm.message}</p>
      <p className=" text-sm text-gray-600">
        {alarm?.title?.includes("초대") ? (
          <div className=" flex items-center justify-center gap-4">
            <span
              className=" flex items-center gap-1 mt-1 cursor-pointer"
              onClick={() => mutate(Number(alarm.simulationSeq))}
            >
              <AiOutlineCheckCircle
                style={{
                  fontSize: "20px",
                  color: "green",
                }}
              />
              <span className=" text-green-700">수락</span>
            </span>
            <span className=" flex items-center gap-1 mt-1 cursor-pointer" onClick={onReadAlarm}>
              <AiOutlineCloseCircle
                style={{
                  fontSize: "20px",
                  color: "red",
                }}
              />
              <span className=" text-red-700">거절</span>
            </span>
          </div>
        ) : (
          <span className=" flex items-center gap-1 mt-1 cursor-pointer" onClick={onReadAlarm}>
            <AiOutlineCheckCircle
              style={{
                fontSize: "15px",
                color: "green",
              }}
            />
            <span className=" text-green-700">확인</span>
          </span>
        )}
      </p>
    </div>
  );
};
export default HeaderAlarmItem;
