import { Popover, Transition, Listbox } from "@headlessui/react";
import { Fragment } from "react";
import { PiWechatLogoLight } from "react-icons/pi";
import ChattingArea from "./ChattingArea";
import { useState } from "react";
import { RiExpandUpDownLine } from "react-icons/ri";
import { AiOutlineCheck } from "react-icons/ai";
import { useQuery } from "react-query";
import { getMyGroupList } from "../../api/group";
import { MyGroup } from "../../type/Group";
import stompStore from "../../store/stompStore";
import userStore from "../../store/userStore";

const ChatIcon = () => {
  const { client } = stompStore();
  const { userInfo, accessToken } = userStore();
  const [curChatGroup, setCurChatGroup] = useState<MyGroup>();
  const [message, setMessage] = useState("");
  const onChangeMessage = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(e.target.value);
  };

  const { data } = useQuery(["myGroupList"], getMyGroupList, {
    onSuccess: (data) => {
      const notFinishedGroup = data.filter((group) => group.progressState !== "finished");
      setCurChatGroup(notFinishedGroup[0]);
    },
    enabled: !!accessToken,
  });
  const onSendMessage = () => {
    if (message.trim() === "") return;
    const sendData = {
      type: "message",
      simulationSeq: curChatGroup?.simulationSeq,
      userSeq: userInfo?.seq,
      message,
      dateTime: new Date(),
      profileImgSearchName: userInfo?.profileImgSearchName,
      nickname: userInfo?.nickname,
    };
    client?.publish({
      destination: `/app/chat.message.${curChatGroup?.simulationSeq}`,
      body: JSON.stringify(sendData),
    });
    setMessage("");
  };

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
            <Popover.Panel className="absolute bottom-[120px] border-2 right-0 w-96 h-[450px] rounded-md bg-slate-100 ">
              <div className="h-full flex flex-col absolute w-full rounded-md ">
                <div className="w-full  bg-mainDark py-2 text-white flex justify-between items-center shadow-md rounded-md">
                  <div className="text-green-100 ms-2 font-semiBold text-lg tracking-wide">{curChatGroup?.title}</div>
                  <Listbox value={curChatGroup} onChange={setCurChatGroup} by="simulationSeq">
                    <div className="relative w-1/2">
                      <Listbox.Button className="relative w-11/12 mr-2 cursor-default rounded-lg bg-white py-2 pl-3 pr-10 text-left shadow-md focus:outline-none focus-visible:border-indigo-500 focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75 focus-visible:ring-offset-2 focus-visible:ring-offset-orange-300 sm:text-sm">
                        <span className="block truncate text-black">{curChatGroup?.title}</span>
                        <span className="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-2">
                          <RiExpandUpDownLine className="h-5 w-5 text-gray-400" />
                        </span>
                      </Listbox.Button>
                      <Transition
                        as={Fragment}
                        leave="transition ease-in duration-100"
                        leaveFrom="opacity-100"
                        leaveTo="opacity-0"
                      >
                        <Listbox.Options className="absolute mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                          {data
                            ?.filter((group) => group.progressState !== "finished")
                            ?.map((myGroup, index) => (
                              <Listbox.Option
                                key={index}
                                className={({ active }) =>
                                  `relative cursor-default select-none py-2 pl-10 pr-4 ${
                                    active ? "bg-amber-100 text-amber-900" : "text-gray-900"
                                  }`
                                }
                                value={myGroup}
                              >
                                {({ selected }) => (
                                  <>
                                    <span className={`block truncate ${selected ? "font-medium" : "font-normal"}`}>
                                      {myGroup.title}
                                    </span>
                                    {selected && (
                                      <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-amber-600">
                                        <AiOutlineCheck />
                                      </span>
                                    )}
                                  </>
                                )}
                              </Listbox.Option>
                            ))}
                        </Listbox.Options>
                      </Transition>
                    </div>
                  </Listbox>
                </div>
                <ChattingArea simulationSeq={curChatGroup?.simulationSeq || 0} />
                <div className="w-full flex justify-around bg-green-100 items-center gap-2">
                  <input
                    className=" overflow-y-hidden flex-grow m-2 py-3 px-4 mr-1 rounded-full border border-gray-300 bg-gray-200 resize-none text-sm items-center"
                    placeholder={
                      curChatGroup?.simulationSeq === undefined
                        ? "참여한 그룹이 없어요."
                        : "전송할 메세지를 입력해주세요."
                    }
                    disabled={curChatGroup?.simulationSeq === undefined}
                    style={{ outline: "none" }}
                    onChange={onChangeMessage}
                    onKeyUp={(e) => {
                      if (e.key === "Enter") {
                        onSendMessage();
                      }
                    }}
                    value={message}
                  ></input>
                  <button
                    type="button"
                    className={`
                    ${curChatGroup?.simulationSeq === undefined ? " bg-myGray " : "bg-mainDark hover:bg-mainMoreDark"}
                    h-3/4 mr-1 inline-flex items-center justify-center rounded-lg px-2 transition duration-500 ease-in-out text-white focus:outline-none`}
                    onClick={onSendMessage}
                    disabled={curChatGroup?.simulationSeq === undefined}
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
