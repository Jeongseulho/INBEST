interface Props {
  percent: number;
}

const IncreaseIcon = ({ percent }: Props) => {
  return (
    <div className=" flex items-center">
      <div id="increase-triangle"></div>
      <p>{percent}</p>
    </div>
  );
};
export default IncreaseIcon;
