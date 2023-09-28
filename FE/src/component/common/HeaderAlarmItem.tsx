import { Menu } from "@headlessui/react";
import { AiOutlineCheckCircle } from "react-icons/ai";

const HeaderAlarmItem = () => {
  return (
    <Menu.Item>
      <div className=" flex flex-col px-4 py-2">
        <p className=" text-sm font-bold">주식 체결 알림</p>
        <p className=" text-xs text-gray-600">
          매도 신청한 xx 주식이 xx에 체결되었습니다.
          <span className=" flex items-center gap-1 mt-1 cursor-pointer">
            <AiOutlineCheckCircle
              style={{
                fontSize: "15px",
                color: "green",
              }}
            />
            <span className=" text-green-700">확인</span>
          </span>
        </p>
      </div>
    </Menu.Item>
  );
};
export default HeaderAlarmItem;
