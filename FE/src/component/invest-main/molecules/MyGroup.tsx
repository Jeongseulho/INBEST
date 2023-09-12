const MyGroup = () => {
  const isProgress = true;
  const isComplete = false;
  const isWaiting = false;

  if (isProgress) return <div className=" shadow-component w-24 h-24">진행중인 그룹</div>;
  if (isComplete) return <div>완료된 그룹</div>;
  if (isWaiting) return <div>대기중인 그룹</div>;
};
export default MyGroup;
