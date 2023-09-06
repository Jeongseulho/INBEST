import { motion, AnimatePresence } from "framer-motion";

interface StepDescProps {
  title: string;
}

const StepDesc = ({ title }: StepDescProps) => {
  return (
    <AnimatePresence>
      <motion.div
        key={title}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        transition={{ duration: 0.5 }}
        className="text-white"
      >
        <h1>{title}</h1>
      </motion.div>
    </AnimatePresence>
  );
};
export default StepDesc;
