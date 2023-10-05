import { tierToString } from "../../../util/tierToString";
import ToTierImg from "./ToTierImg";
import { useMemberTierInfo } from "./useMemberTierInfo";
const MemberTierInfo = ({ tier, userCnt }: { tier: number; userCnt: number }) => {
  const { currentRank } = useMemberTierInfo();
  return (
    <div className="sm:hidden md:flex items-center">
      <ToTierImg tier={tier} w={80} />
      <div className="ms-5">
        <div className="mb-3">티어 정보</div>
        <div className="flex justify-between mb-2">
          <span
            className={`${
              tierToString(tier) === "브론즈"
                ? "text-amber-900"
                : tierToString(tier) === "실버"
                ? "text-slate-400"
                : tierToString(tier) === "골드"
                ? "text-yellow-500"
                : "text-blue-300"
            }`}
          >
            {tierToString(tier)}
          </span>
          <span
            className={`${
              tierToString(tier) === "브론즈"
                ? "text-amber-900"
                : tierToString(tier) === "실버"
                ? "text-slate-400"
                : tierToString(tier) === "골드"
                ? "text-yellow-500"
                : "text-blue-300"
            }`}
          >
            {tier % 100}P
          </span>
        </div>

        <div className="relative w-56">
          <div className="bg-green-100 border-black border-opacity-30 rounded-md border h-8"></div>
          <div
            className={"rounded-md bg-green-500 left-0 h-full absolute top-0"}
            style={{ width: tier >= 300 ? "100%" : `${tier % 100}%`, height: "100%" }}
          ></div>
        </div>
        <div className="flex justify-between mt-2">
          {currentRank && (
            <span>
              상위{"  "}
              {Math.round((currentRank / userCnt) * 10000) / 100}
              <span className="text-sm">%</span>
            </span>
          )}
          <span>{currentRank}위</span>
        </div>
      </div>
    </div>
  );
};
export default MemberTierInfo;
