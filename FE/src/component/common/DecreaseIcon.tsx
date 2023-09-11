interface Props {
  percent: number;
}

const DecreaseIcon = ({ percent }: Props) => {
  return (
    <div className=" flex items-center">
      <div id="decrease-triangle"></div>
      <p>{percent}</p>
    </div>
  );
};
export default DecreaseIcon;
