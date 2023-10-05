import { motion } from "framer-motion";

interface Props {
  title: string;
  desc: string;
}

const StepPaginationDesc = ({ title, desc }: Props) => {
  return (
    <motion.div
      key={title}
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      transition={{ duration: 0.5 }}
      className="text-white relative mt-20"
    >
      <p className=" absolute text-8xl drop-shadow-xl">{title}</p>
      <h2 className=" absolute top-40 drop-shadow-xl">{desc}</h2>
    </motion.div>
  );
};
export default StepPaginationDesc;
