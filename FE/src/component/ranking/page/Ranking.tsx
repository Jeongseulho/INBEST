import RankingSidebar from "../organisms/RankingSidebar";
import { useRanking } from "./useRanking";
import { Outlet } from "react-router-dom";

const Ranking = () => {
  const { activeTab, setActiveTab } = useRanking();
  return (
    <div className="flex">
      <RankingSidebar activeTab={activeTab} setActiveTab={setActiveTab} />
      <div className="flex justify-center w-full">
        <Outlet />
      </div>
    </div>
  );
};
export default Ranking;
