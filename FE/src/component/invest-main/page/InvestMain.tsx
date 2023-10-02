import CreateModal from "../organisms/CreateModal";
import FilterModal from "../organisms/FilterModal";
import GroupList from "../organisms/GroupList";
import MyGroupList from "../organisms/MyGroupList";
import InvestingTotalInfo from "../organisms/InvestingTotalInfo";
import WaitingGroupModal from "../organisms/WaitingGroupModal";
import InProgressGroupModal from "../organisms/InProgressGroupModal";
import QuestionJoinModal from "../organisms/QuestionJoinModal";
import { useQuery } from "react-query";
import { getMyGroupList, getJoinableGroupList } from "../../../api/group";
import { useFilterModal } from "../organisms/useFilterModal";
import { useState } from "react";

const InvestMain = () => {
  const {
    data: myGroupList,
    isLoading: isLoadingMyGroupList,
    refetch: refetchMyGroupList,
  } = useQuery(["myGroupList"], getMyGroupList, {
    cacheTime: 0,
    staleTime: 0,
    retry: 3,
  });
  const {
    data: joinableGroupList,
    isLoading: isLoadingJoinableGroupList,
    refetch: refetchJoinableGroupList,
  } = useQuery(["joinableGroupList"], getJoinableGroupList, {
    cacheTime: 0,
    staleTime: 0,
    retry: 3,
  });
  const { activeTab, setActiveTab, groupFilter, dispatch, initGroupFilter } = useFilterModal();
  const [filter, setFilter] = useState(initGroupFilter);

  return (
    <div className=" flex flex-col items-center gap-10 w-9/12 mx-auto mt-10">
      <InvestingTotalInfo />
      <CreateModal refetchMyGroupList={refetchMyGroupList} refetchJoinableGroupList={refetchJoinableGroupList} />
      <FilterModal
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        groupFilter={groupFilter}
        dispatch={dispatch}
        setFilter={setFilter}
      />
      <WaitingGroupModal refetchMyGroupList={refetchMyGroupList} refetchJoinableGroupList={refetchJoinableGroupList} />
      <InProgressGroupModal />
      <QuestionJoinModal refetchMyGroupList={refetchMyGroupList} refetchJoinableGroupList={refetchJoinableGroupList} />
      <MyGroupList data={myGroupList} isLoading={isLoadingMyGroupList} />
      <GroupList filter={filter} data={joinableGroupList} isLoading={isLoadingJoinableGroupList} />
    </div>
  );
};
export default InvestMain;
