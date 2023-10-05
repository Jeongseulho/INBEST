import { Link } from "react-router-dom";
import { Participants } from "../../../type/MemberInfo";

const SimulationIsMembers = ({ members, seq }: { members: Participants[]; seq: number }) => {
  return (
    <div className="h-full grid grid-cols-2 w-4/12  overflow-auto pt-3">
      {members.map((member, idx) => (
        <Link to={`/profile/${member.userSeq}`} key={idx}>
          <div
            className={`flex items-center ms-2 hover:cursor-pointer px-1 h-10 hover:bg-black hover:bg-opacity-20 rounded-lg ${
              seq === member.userSeq && "bg-black bg-opacity-10"
            }`}
          >
            <img src={member.profileImgSearchName} className="w-7 me-2 rounded-full" />
            <p className="line-clamp-1">{member.nickname}</p>
          </div>
        </Link>
      ))}
    </div>
  );
};
export default SimulationIsMembers;
