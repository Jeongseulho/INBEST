import { Popover, Transition } from "@headlessui/react";
import { Fragment } from "react";
import { PiWechatLogoLight } from "react-icons/pi";
import default_image from "../../asset/image/default_image.png";
import ChatContentItem from "./ChatContentItem";
// import { useState } from "react";

const ChatIcon = () => {
  // const [chatTab, setChatTab] = useState("");
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
              <div className="h-full flex flex-col absolute w-full ">
                <div className="w-full h-1/2 flex justify-around items-center py-4 text-gray-500">
                  <p className="font-regular text-md">안녕하세요</p>
                  <p className="font-regular text-md">안녕하세요</p>
                  <p className="font-regular text-md">안녕하세요</p>
                </div>
                <div className="w-full bg-mainDark pt-2 text-white flex justify-center items-center shadow-md">
                  <div className="text-green-100 font-semiBold text-lg tracking-wide">그룹 이름 : 그룹 이름 적당히</div>
                </div>
                <div className="overflow-y-scroll flex-col min-h-[80%]">
                  <ChatContentItem isMine={true} message={"상대방 메세지"} profileImage={default_image} />
                  <ChatContentItem isMine={false} message={"내 메세지"} />
                </div>
                <div className="w-full flex justify-around bg-green-100 items-center gap-2">
                  <textarea
                    className="flex-grow m-2 py-3 px-4 mr-1 rounded-full border border-gray-300 bg-gray-200 resize-none text-sm items-center"
                    rows={1}
                    placeholder="Message..."
                    style={{ outline: "none" }}
                  ></textarea>
                  <button
                    type="button"
                    className=" h-3/4 mr-1 inline-flex items-center justify-center rounded-lg px-2 transition duration-500 ease-in-out text-white bg-mainDark hover:bg-mainMoreDark focus:outline-none"
                  >
                    <span className="font-bold text-sm">Send</span>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                      className="h-4 w-4 ml-2 transform rotate-90"
                    >
                      <path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"></path>
                    </svg>
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
