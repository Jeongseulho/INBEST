import { Outlet } from "react-router-dom";

const Community = () => {
  return (
    <>
      <div className="flex">
        <div className="w-96"></div>
        <div className="w-full">
          <div className="w-5/6">
            <header className="border-b-2 border-black h-10"></header>
            <Outlet />
          </div>
        </div>
      </div>
    </>
  );
};
export default Community;
