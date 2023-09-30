import decrease_graph from "../../asset/image/decrease_graph.png";
import { AiFillQuestionCircle } from "react-icons/ai";
import increase_graph from "../../asset/image/increase_graph.png";
import Skeleton from "react-loading-skeleton";

interface Props {
  title: string;
  desc: string;
  state: 0 | 1 | undefined;
  isLoading: boolean;
}

const GraphIconComponent = ({ title, desc, state, isLoading }: Props) => {
  return (
    <div className=" shadow-component p-4 flex flex-col items-center">
      <div className=" flex items-center gap-2">
        <h4>{title}</h4>
        <div className="group relative">
          <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
          <span className=" bg-opacity-80 z-50 hidden group-hover:block text-sm text-white bg-gray-500 rounded px-2 py-1 absolute -top-8 left-1/2 transform -translate-x-1/2 w-52">
            {desc}
          </span>
        </div>
      </div>
      {isLoading ? (
        <div className=" flex flex-col items-center gap-2">
          <Skeleton width={120} height={120} />
          <Skeleton width={60} height={20} />
        </div>
      ) : (
        <>
          {state !== undefined && state > 0 ? (
            <div className=" flex flex-col items-center gap-2">
              <img src={increase_graph} width={120} />
              <h5 className=" text-mainMoreDark">상승중</h5>
            </div>
          ) : (
            <div className=" flex flex-col items-center gap-2">
              <img src={decrease_graph} width={120} />
              <h5 className=" text-myRed">하강중</h5>
            </div>
          )}
        </>
      )}
    </div>
  );
};
export default GraphIconComponent;
