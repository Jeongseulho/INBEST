import { Popover, Transition } from "@headlessui/react";
import { Fragment } from "react";
import { PiWechatLogoLight } from "react-icons/pi";

const ChatIcon = () => {
  //  <div className=" cursor-pointer fixed right-10 bottom-10 hover:scale-110 transition-transform duration-300">
  //    <img src={chat} width={80} />
  //  </div>

  return (
    <div className=" fixed right-10 bottom-10">
      <Popover className="relative">
        <>
          <Popover.Button
            id="chat-icon"
            as="div"
            className=" w-[80px] h-[80px] rounded-3xl flex items-center justify-center"
          >
            <PiWechatLogoLight
              className="w-[50px] h-[50px]"
              style={{
                color: "#35493a",
              }}
            />
          </Popover.Button>
          <Transition
            as={Fragment}
            enter="transition ease-out duration-200"
            enterFrom="opacity-0 translate-y-1"
            enterTo="opacity-100 translate-y-0"
            leave="transition ease-in duration-150"
            leaveFrom="opacity-100 translate-y-0"
            leaveTo="opacity-0 translate-y-1"
          >
            <Popover.Panel className="absolute bottom-96 border-2">
              <div>안녕</div>
            </Popover.Panel>
          </Transition>
        </>
      </Popover>
    </div>
  );
};
export default ChatIcon;
