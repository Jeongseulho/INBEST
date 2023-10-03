import { Popover, Transition } from "@headlessui/react";
import { Fragment } from "react";
import { PiWechatLogoLight } from "react-icons/pi";

const ChatIcon = () => {
  //  <div className=" cursor-pointer fixed right-10 bottom-10 hover:scale-110 transition-transform duration-300">
  //    <img src={chat} width={80} />
  //  </div>

  return (
    <div className=" fixed right-10 bottom-10 z-20">
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
            <Popover.Panel className="absolute bottom-24 border-2 right-0 w-96 h-[500px] rounded-md bg-slate-100 ">
              <div className="h-full flex flex-col">
                <div className="w-full bg-mainDark h-12 pt-2 text-white flex justify-center items-center shadow-md">
                  <div className="text-green-100 font-bold text-lg tracking-wide">title</div>
                </div>
                <div className="overflow-y-scroll flex-col">
                  <div className="clearfix flex justify-start">
                    <div className="bg-gray-300 w-3/4 mx-4 my-2 p-2 rounded-lg clearfix text-sm">
                      this is a basic mobile chat layout, build with tailwind css
                    </div>
                  </div>
                  <div className="clearfix flex justify-start">
                    <div className="bg-gray-300 w-3/4 mx-4 my-2 p-2 rounded-lg clearfix text-sm">
                      this is a basic mobile chat layout, build with tailwind css
                    </div>
                  </div>
                  <div className="clearfix flex justify-start">
                    <div className="bg-gray-300 w-3/4 mx-4 my-2 p-2 rounded-lg clearfix text-sm">
                      this is a basic mobile chat layout, build with tailwind css
                    </div>
                  </div>
                  <div className="clearfix flex justify-start">
                    <div className="bg-gray-300 w-3/4 mx-4 my-2 p-2 rounded-lg clearfix text-sm">
                      this is a basic mobile chat layout, build with tailwind css
                    </div>
                  </div>
                  <div className="clearfix flex justify-end">
                    <div className="bg-green-300 w-3/4 mx-4 my-2 p-2 rounded-lg clearfix text-sm">
                      check my twitter to see when it will be released.
                    </div>
                  </div>
                  <div className="clearfix flex justify-end">
                    <div className="bg-green-300 w-3/4 mx-4 my-2 p-2 rounded-lg clearfix text-sm">
                      check my twitter to see when it will be released.
                    </div>
                  </div>
                </div>
                <div className="w-full flex justify-between bg-green-100">
                  <textarea
                    className="flex-grow m-2 py-3 px-4 mr-1 rounded-full border border-gray-300 bg-gray-200 resize-none text-sm items-center"
                    rows={1}
                    placeholder="Message..."
                    style={{ outline: "none" }}
                  ></textarea>
                  <button
                    className="m-2"
                    style={{
                      outline: "none",
                    }}
                  >
                    {/* Send 버튼 아이콘 */}
                  </button>
                </div>
              </div>
            </Popover.Panel>
          </Transition>
        </>
      </Popover>
    </div>
  );
};
export default ChatIcon;
