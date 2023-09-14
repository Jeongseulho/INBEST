import { Link } from "react-router-dom";
import temp_logo from "../../asset/image/temp_logo.png";
import defaultImg from "../../asset/image/default_image.png";
import { Fragment } from "react";
import { Menu, Transition } from "@headlessui/react";
import { FiLogOut } from "react-icons/fi";
import { AiFillProfile } from "react-icons/ai";
import { BsPencilSquare } from "react-icons/bs";
import ProfileUpdate from "../account/page/ProfileUpdate";
import { useState } from "react";
function classNames(...classes: string[]) {
  return classes.filter(Boolean).join(" ");
}

const Header = () => {
  const [showModal, setShowModal] = useState(false);

  return (
    <header className="w-full h-[8vh] flex justify-between items-center bg-gray-50  bg-opacity-90">
      <Link to="/">
        <img className="h-[9vh] mx-24 " src={temp_logo} />
      </Link>
      <a className=" text-center text-xl">모의투자 게임</a>
      <a className=" text-center text-xl">랭킹</a>
      <a className=" text-center text-xl">게시판</a>
      <a className=" text-center text-xl">금융 상품 추천</a>
      <a className=" text-center text-xl">금융 사전</a>
      <a className=" text-center text-xl">관리자 페이지</a>
      <Link to="/login" className=" text-center me-56 text-xl">
        로그인
      </Link>
      {/* <div className=" hover:cursor-pointer">프로필 사진</div> */}
      <Menu as="div" className="relative inline-block text-left me-10">
        <div style={{ width: "64px", height: "64px" }} className="flex justify-center items-center">
          <Menu.Button
            as="img"
            src={defaultImg}
            className="rounded-full hover:cursor-pointer shadow-md"
            width={50}
            height={50}
          />
        </div>

        <Transition
          as={Fragment}
          enter="transition ease-out duration-100"
          enterFrom="transform opacity-0 scale-95"
          enterTo="transform opacity-100 scale-100"
          leave="transition ease-in duration-75"
          leaveFrom="transform opacity-100 scale-100"
          leaveTo="transform opacity-0 scale-95"
        >
          <Menu.Items className=" z-20 absolute -right-1  mt-2 w-44 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
            <div className="py-1">
              <Menu.Item>
                {({ active }) => (
                  <a
                    href="#"
                    className={classNames(
                      active ? "bg-gray-100 text-gray-900" : "text-gray-700",
                      " px-4 py-2  flex items-center"
                    )}
                  >
                    <AiFillProfile />
                    <span className="ms-3">마이페이지</span>
                  </a>
                )}
              </Menu.Item>
              <Menu.Item>
                {({ active }) => (
                  <div
                    className={classNames(
                      active ? "bg-gray-100 text-gray-900" : "text-gray-700",
                      " px-4 py-2 flex items-center"
                    )}
                  >
                    <BsPencilSquare />
                    <span className="ms-3">
                      <button onClick={() => setShowModal(() => true)}>회원정보수정</button>
                    </span>
                  </div>
                )}
              </Menu.Item>
              <Menu.Item>
                {({ active }) => (
                  <a
                    href="#"
                    className={classNames(
                      active ? "bg-gray-100 text-gray-900" : "text-gray-700",
                      "px-4 py-2 flex items-center text-red-500"
                    )}
                  >
                    <FiLogOut />
                    <span className="ms-3">로그아웃</span>
                  </a>
                )}
              </Menu.Item>
            </div>
          </Menu.Items>
        </Transition>
      </Menu>
      <ProfileUpdate showModal={showModal} setShowModal={setShowModal} />
    </header>
  );
};
export default Header;
