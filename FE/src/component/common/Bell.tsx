import { Popover, Transition } from "@headlessui/react";
import HeaderAlarmItem from "./HeaderAlarmItem";
import bell from "../../asset/image/bell.png";
import { Fragment } from "react";
import { useHeaderAlarm } from "./useHeaderAlarm";
const Bell = () => {
  const { alarmList, setAlarmList } = useHeaderAlarm();
  return (
    <Popover as="div" className="relative inline-block text-left me-10">
      <div className=" rounded-full w-6 h-6 bg-red-600 absolute z-10 left-6 text-center text-white">
        {alarmList.length}
      </div>
      <Popover.Button as="img" src={bell} id="bell" className={"cursor-pointer "} width={45} />

      <Transition
        as={Fragment}
        enter="transition ease-out duration-100"
        enterFrom="transform opacity-0 scale-95"
        enterTo="transform opacity-100 scale-100"
        leave="transition ease-in duration-75"
        leaveFrom="transform opacity-100 scale-100"
        leaveTo="transform opacity-0 scale-95"
      >
        <Popover.Panel className=" z-20 absolute -left-6  mt-4 w-44 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
          <div className="py-1">
            {alarmList.length > 0 ? (
              alarmList.map((alarm) => (
                <HeaderAlarmItem key={alarm.id} setAlarmList={setAlarmList} alarm={alarm} id={alarm.id} />
              ))
            ) : (
              <div className=" flex flex-col px-4 py-2">
                <p className=" text-sm font-bold text-gray-600">알림이 없습니다.</p>
              </div>
            )}
          </div>
        </Popover.Panel>
      </Transition>
    </Popover>
  );
};
export default Bell;
