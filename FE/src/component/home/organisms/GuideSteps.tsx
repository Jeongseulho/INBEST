import { motion } from "framer-motion";
import { useObserver } from "../../../hook/useObserver";

/* eslint-disable max-lines-per-function */
const GuideSteps = () => {
  const { ref, animation } = useObserver();

  return (
    <section className="bg-slate-800 text-white">
      <motion.div
        ref={ref}
        initial="hidden"
        animate={animation}
        variants={{
          visible: { opacity: 1 },
          hidden: { opacity: 0 },
        }}
        className=" h-screen"
      >
        STEP0 제목 설명
      </motion.div>

      <motion.div
        ref={ref}
        initial="hidden"
        animate={animation}
        variants={{
          visible: { opacity: 1 },
          hidden: { opacity: 0 },
        }}
        className=" h-screen"
      >
        STEP1 제목 설명
      </motion.div>

      <motion.div
        ref={ref}
        initial="hidden"
        animate={animation}
        variants={{
          visible: { opacity: 1 },
          hidden: { opacity: 0 },
        }}
        className=" h-screen"
      >
        STEP2 제목 설명
      </motion.div>

      <motion.div
        ref={ref}
        initial="hidden"
        animate={animation}
        variants={{
          visible: { opacity: 1 },
          hidden: { opacity: 0 },
        }}
        className=" h-screen"
      >
        STEP3 제목 설명
      </motion.div>

      <motion.div
        ref={ref}
        initial="hidden"
        animate={animation}
        variants={{
          visible: { opacity: 1 },
          hidden: { opacity: 0 },
        }}
        className=" h-screen"
      >
        STEP4 제목 설명
      </motion.div>
    </section>
  );
};
export default GuideSteps;
