import chat from "../../asset/image/chat.png";
import { Popover, Transition } from "@headlessui/react";
import { Fragment } from "react";

const ChatIcon = () => {
  //  <div className=" cursor-pointer fixed right-10 bottom-10 hover:scale-110 transition-transform duration-300">
  //    <img src={chat} width={80} />
  //  </div>

  return (
    <div className=" fixed right-10 bottom-10 z-20">
      <Popover className="relative">
        <>
          <Popover.Button
            as="img"
            src={chat}
            className="cursor-pointer w-[80px] hover:scale-110 transition-transform duration-300"
          ></Popover.Button>
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
