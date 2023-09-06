interface StepDescProps {
  title: string;
  desc: string;
}

const StepDesc = ({ title, desc }: StepDescProps) => {
  return (
    <article className=" text-white">
      <h1>{title}</h1>
      <h3 className=" font-light">{desc}</h3>
    </article>
  );
};
export default StepDesc;
