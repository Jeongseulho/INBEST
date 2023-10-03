import { Menu, Transition } from "@headlessui/react";
import HeaderAlarmItem from "./HeaderAlarmItem";
import bell from "../../asset/image/bell.png";
import { Fragment } from "react";
import { useHeaderAlarm } from "./useHeaderAlarm";

const Bell = () => {
  const { shakeBell, alarmList, setAlarmList } = useHeaderAlarm();
  return (
    <Menu as="div" className="relative inline-block text-left me-10">
      <Menu.Button
        as="img"
        src={bell}
        className={`cursor-pointer ${shakeBell && "animate__animated animate__shakeX animate__infinite"}`}
        width={45}
      />

      <Transition
        as={Fragment}
        enter="transition ease-out duration-100"
        enterFrom="transform opacity-0 scale-95"
        enterTo="transform opacity-100 scale-100"
        leave="transition ease-in duration-75"
        leaveFrom="transform opacity-100 scale-100"
        leaveTo="transform opacity-0 scale-95"
      >
        <Menu.Items className=" z-20 absolute -left-6  mt-4 w-44 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
          <div className="py-1">
            {alarmList.map((alarm, index) => (
              <HeaderAlarmItem
                key={index}
                title={alarm.title}
                content={alarm.message}
                setAlarmList={setAlarmList}
                id={alarm.id}
              />
            ))}
          </div>
        </Menu.Items>
      </Transition>
    </Menu>
  );
};
export default Bell;
