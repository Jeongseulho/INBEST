import decrease_graph from "../../asset/image/decrease_graph.png";
import { AiFillQuestionCircle } from "react-icons/ai";

interface Props {
  title: string;
  desc: string;
}

const DecreaseGraphIcon = ({ title, desc }: Props) => {
  return (
    <div className=" shadow-component p-4">
      <div className=" flex items-center gap-2">
        <h4>{title}</h4>
        <div className="group relative">
          <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
          <span className=" bg-opacity-80 z-50 hidden group-hover:block text-sm text-white bg-gray-500 rounded px-2 py-1 absolute -top-8 left-1/2 transform -translate-x-1/2 w-52">
            {desc}
          </span>
        </div>
      </div>
      <img src={decrease_graph} width={180} />
    </div>
  );
};
export default DecreaseGraphIcon;
