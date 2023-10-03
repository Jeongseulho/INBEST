import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import { useQuery } from "react-query";
import { getGroupSearchTitle } from "../../../api/ranking";
import { useNavigate } from "react-router-dom";
const GroupSearchModal = ({
  setSearchSeq,
  showModal,
  setShowModal,
  inputText,
}: {
  showModal: boolean;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
  setSearchSeq: React.Dispatch<React.SetStateAction<number>>;
  inputText: string;
}) => {
  const { data } = useQuery(["GetGroupSearchTitle", inputText], () => getGroupSearchTitle(inputText));
  const searchList = data?.SearchList;
  const navigate = useNavigate();
  return (
    <>
      <Modal
        isOpen={showModal}
        ariaHideApp={false}
        style={{
          content: {
            ...CONTENT_MODAL_STYLE,
            width: "500px",
            maxHeight: "700px",
            margin: "auto",
            overflow: "clip",
            paddingBottom: "65px",
          },
          overlay: OVERLAY_MODAL_STYLE,
        }}
      >
        <div className=" h-full overflow-auto rounded-t-lg">
          <table className="w-full">
            <thead className="bg-main h-10 ">
              <tr>
                <th className="text-left">
                  <span className="ms-3">그룹명</span>
                </th>
                <th className="text-center">인원</th>
                <th className="text-center">기간</th>
              </tr>
            </thead>
            <tbody>
              {searchList?.map((group, idx) => (
                <tr
                  key={idx}
                  className="border-b-2 h-10 hover:bg-gray-300 hover:cursor-pointer"
                  onClick={() => {
                    setShowModal(false);
                    navigate(`/ranking/group/${group.simulationSeq}`);
                    setSearchSeq(group.simulationSeq);
                  }}
                >
                  <td className="line-clamp-1 overflow-hidden text-ellipsis h-10 flex items-center">{group.title}</td>
                  <td className="text-center">{group.memberNum}명</td>
                  <td className="text-center">{group.period}일</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="flex justify-center mt-3">
          <button className="jongRyul-primary w-16 h-8" onClick={() => setShowModal(false)}>
            닫기
          </button>
        </div>
      </Modal>
    </>
  );
};
export default GroupSearchModal;
